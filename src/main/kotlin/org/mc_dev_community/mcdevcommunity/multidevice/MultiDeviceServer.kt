package org.mc_dev_community.mcdevcommunity.multidevice

import com.intellij.notification.*
import org.mc_dev_community.mcdevcommunity.locale.MDCLocaleUtil
import org.mc_dev_community.mcdevcommunity.util.GROUP_ID
import java.net.*
import java.nio.charset.StandardCharsets
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean

class MultiDeviceServer private constructor(private var ipAddress: String, private var port: Int) {
    companion object {
        private var instance: MultiDeviceServer? = null

        var isRunning = AtomicBoolean(false)
        val inputMessageQueue = LinkedTransferQueue<String>()
        val outputMessageQueue = LinkedTransferQueue<String>()
        @Synchronized
        fun runServer(ipAddress: String, port: Int)  {
            if (instance == null) {
                instance = MultiDeviceServer(ipAddress, port)
                instance!!.serverThread.start()
            }
            if (ipAddress != instance!!.ipAddress || port != instance!!.port)
                instance!!.onChange(ipAddress, port)
            if (!isRunning.get()) {
                isRunning.set(true)
                Notifications.Bus.notify(Notification(
                    GROUP_ID,
                    MDCLocaleUtil.message("MC-Dev-Community.Config.MultiDevice"),
                    MDCLocaleUtil.message("MC-Dev-Community.Notification.ServerStart","$ipAddress:$port"),
                    NotificationType.INFORMATION), null
                )
            }
        }

        @Synchronized
        fun stopServer() {
            if (instance != null && isRunning.get())  {
                Notifications.Bus.notify(Notification(
                    GROUP_ID,
                    MDCLocaleUtil.message("MC-Dev-Community.Config.MultiDevice"),
                    MDCLocaleUtil.message("MC-Dev-Community.Notification.ServerStop"),
                    NotificationType.INFORMATION), null
                )
                instance!!.stopServer()
            }
        }
    }

    @set:Synchronized
    private var serverSocket = ServerSocket(port, 10, InetAddress.getByName(ipAddress))
    private val serverThread = Thread {
        try {
            while (true)  {
                if (!isRunning.get()) continue
                val client = serverSocket.accept()

                Notifications.Bus.notify(Notification(
                    GROUP_ID,
                    MDCLocaleUtil.message("MC-Dev-Community.Config.MultiDevice"),
                    MDCLocaleUtil.message("MC-Dev-Community.Notification.ClientConnected"),
                    NotificationType.INFORMATION), null
                )

                val out = client.getOutputStream()
                out.write("Hello from server".toByteArray())
                out.flush()

                while (true) {
                    val buffer = ByteArray(1024)
                    val length = client.getInputStream().read(buffer)
                    if (length == -1) {
                        Notifications.Bus.notify(Notification(
                            GROUP_ID,
                            MDCLocaleUtil.message("MC-Dev-Community.Config.MultiDevice"),
                            MDCLocaleUtil.message("MC-Dev-Community.Notification.ClientDisconnected"),
                            NotificationType.INFORMATION), null
                        )
                        break
                    }
                    val message = String(buffer, 0, length, StandardCharsets.UTF_8)
                    if (message == "exit") {
                        break
                    }
                    inputMessageQueue.add(message)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Synchronized
    private fun stopServer() {
        try {
            serverSocket.close()
        } catch (_: SocketException) { }
        isRunning.set(false)
    }

    @Synchronized
    private fun onChange(ipAddress: String, port: Int) {
        isRunning.set(false)
        this.ipAddress = ipAddress
        this.port = port
        serverSocket = ServerSocket(port, 10, InetAddress.getByName(ipAddress))
        Notifications.Bus.notify(Notification(
            GROUP_ID,
            MDCLocaleUtil.message("MC-Dev-Community.Config.MultiDevice"),
            MDCLocaleUtil.message("MC-Dev-Community.Notification.ServerIpChange", "$ipAddress:$port"),
            NotificationType.INFORMATION), null
        )
        isRunning.set(true)
    }
}