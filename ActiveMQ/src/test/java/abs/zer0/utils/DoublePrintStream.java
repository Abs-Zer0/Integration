/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class DoublePrintStream extends PrintStream {

    private PrintStream output;

    public DoublePrintStream(OutputStream first, PrintStream second) {
        super(first);
        this.output = second;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
        this.output.write(buf, off, len);
    }

    @Override
    public void write(int b) {
        super.write(b);
        this.output.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        this.output.write(b);
    }

    @Override
    public void flush() {
        super.flush();
        this.output.flush();
    }

    public void firstClose() {
        super.close();
    }

    public void secondClose() {
        this.output.close();
    }

}
