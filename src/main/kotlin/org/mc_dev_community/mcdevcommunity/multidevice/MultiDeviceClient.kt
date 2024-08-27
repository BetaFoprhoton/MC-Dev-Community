package org.mc_dev_community.mcdevcommunity.multidevice

import com.intellij.notification.*
import org.mc_dev_community.mcdevcommunity.locale.MDCLocaleUtil
import org.mc_dev_community.mcdevcommunity.util.GROUP_ID
import java.net.*
import java.nio.charset.StandardCharsets
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean

class MultiDeviceClient private constructor(private var ipAddress: String, private var port: Int) {
    companion object {
        private var instance: MultiDeviceClient? = null

        var isRunning = AtomicBoolean(false)
        val messageQueue = LinkedTransferQueue<String>()
        @Synchronized
        fun runClient(ipAddress: String, port: Int)  {
            if (instance == null) {
                instance = MultiDeviceClient(ipAddress, port)
                instance!!.clientThread.start()
            }
            if (ipAddress != instance!!.ipAddress || port != instance!!.port)
                instance!!.onChange(ipAddress, port)
            if (!isRunning.get()) {
                isRunning.set(true)
            }
        }

        @Synchronized
        fun stopClient() {
            if (instance != null && isRunning.get())  {
                instance!!.stopServer()
            }
        }
    }

    @set:Synchronized
    private var server = Socket(InetAddress.getByName(ipAddress), port)
    private val clientThread = Thread {
        try {
            while (true)  {
                if (!isRunning.get()) continue
                println("Connect to the server " + server.getRemoteSocketAddress());

                val input = server.getInputStream()
                val buffer = ByteArray(1024)
                val len = input.read(buffer);
                val str = String(buffer, 0, len, StandardCharsets.UTF_8);
                println("Received message from the server: $str")
                messageQueue.add(str)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Synchronized
    private fun stopServer() {
        try {
            server.close()
        } catch (_: SocketException) { }
        isRunning.set(false)
    }

    @Synchronized
    private fun onChange(ipAddress: String, port: Int) {
        isRunning.set(false)
        this.ipAddress = ipAddress
        this.port = port
        server = Socket(InetAddress.getByName(ipAddress), port)
        isRunning.set(true)
    }
}