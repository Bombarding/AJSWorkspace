package robot;

import java.awt.image.*;
import java.io.*;
import com.sun.image.codec.jpeg.*;
import java.awt.Color;
import java.nio.ByteBuffer;
import javax.media.opengl.GL;

public class SaveJPGImage {

    public SaveJPGImage() {
    }

    /** Converts the current frame buffer into a jpg image and saves the image to disk. 
     * 
     * @param   name  the realtive path name of the image to be created. Note, if just 
     *    a name is given (i.e. no path) then Netbeans will store this in the 
     *    top level of the project folder.  Thus, if name = "myImage" and 
     *    the imageIndex is 0, then 
     *    this method will create a jpg image file in the project folder called myImage0.jpg
     * @param frameWidth  width of the image (same as width of frame buffer)
     * @param frameHeight height of the image (same as height of frame buffer)
     * @param gl  opengl context
     * @param imageIndex  tacks on this number to  the image file 
     *     (used in animation when mutliple images are 
     *    to be saved.)
     */
    public static void saveFrameBuffer(String name, int frameWidth, int frameHeight, GL gl, int imageIndex) {

        // Create an buffer of the right size to place the data from the frame buffer
        int size = frameWidth * frameHeight;
        System.out.println("width = " + frameWidth + "  height = " + frameHeight);
        ByteBuffer myByteBuffer = ByteBuffer.allocate(3 * size);

        // Grabs the frame buffer data and dumps it into the
        // byte buffer called myByteBuffer.  
        gl.glReadPixels(0, 0, frameWidth, frameHeight, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, myByteBuffer);


        // Create a place to store the pixels once we get them out of the byte buffer.
        BufferedImage image = new BufferedImage(
                frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < frameHeight; i++) {
            for (int j = 0; j < frameWidth; j++) {
                int index = 3 * (i * frameWidth + j);
                // we do a bitwise "and" because otherwise we can end up with negative values.
                // There is probably a better way to do all of this.
                int red = myByteBuffer.get(index) & 0xFF;
                int green = myByteBuffer.get(index + 1) & 0xFF;
                int blue = myByteBuffer.get(index + 2) & 0xFF;
                int color =  0xFF000000 | (red << 16)  | (green << 8)  | blue ;
                image.setRGB(j, frameHeight - i - 1, color);
            }
        }

        // Save jpg image out to disk 
        String imageName = name + imageIndex + ".jpg";
        if (image != null) {
            try {
                OutputStream out = new FileOutputStream(imageName);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(image);
                out.close();
                System.out.println("Image " + imageName + " saved.");
            } catch (Exception e) {
                System.out.println("Error in saveImage.\n" + e);
            }
        }
    }
}
