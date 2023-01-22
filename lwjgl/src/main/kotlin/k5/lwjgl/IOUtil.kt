package k5.lwjgl

import org.lwjgl.BufferUtils
import org.lwjgl.system.MemoryUtil
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.file.Files
import java.nio.file.Paths

object IOUtil {
    private fun resizeBuffer(buffer: ByteBuffer, newCapacity: Int): ByteBuffer {
        return BufferUtils.createByteBuffer(newCapacity).apply { put(buffer.flip()) }
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     *
     * @return the resource data
     *
     * @throws IOException if an IO error occurs
     */
    @Throws(IOException::class)
    fun ioResourceToByteBuffer(resource: String, bufferSize: Int): ByteBuffer {
        var buffer: ByteBuffer
        val path = Paths.get(resource)
        if (Files.isReadable(path)) Files.newByteChannel(path).use { fc ->
            buffer = BufferUtils.createByteBuffer(fc.size().toInt() + 1)
            while (fc.read(buffer) != -1) {
                //
            }
        } else IOUtil::class.java.classLoader.getResourceAsStream(resource)!!.use { source ->
            Channels.newChannel(source).use { rbc ->
                buffer = BufferUtils.createByteBuffer(bufferSize)
                while (rbc.read(buffer) != -1) {
                    if (buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2) // 50%
                }
            }
        }
        return MemoryUtil.memSlice(buffer.flip())
    }
}
