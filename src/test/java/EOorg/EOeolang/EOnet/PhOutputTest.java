/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 kerelape
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
// @checkstyle PackageNameCheck (1 line)
package EOorg.EOeolang.EOnet;

import EOorg.EOeolang.EOseq;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * PhOutput tests.
 *
 * @since 0.0.0
 * @checkstyle ClassDataAbstractionCouplingCheck (100 lines)
 */
public final class PhOutputTest {

    /**
     * Tests that PhOutput can write.
     */
    @Test
    public void writes() {
        final byte data = (byte) new Random().nextInt();
        final AtomicInteger written = new AtomicInteger(-1);
        final OutputStream stream = new OutputStream() {
            @Override
            public void write(final int data) {
                written.set((byte) data);
            }
        };
        final PhOutput output = new PhOutput(Phi.Φ, stream);
        new Dataized(
            new PhWith(
                new PhWith(
                    new EOseq(Phi.Φ),
                    0,
                    new PhWith(
                        output.attr("write").get(),
                        "data",
                        new Data.ToPhi((long) data)
                    )
                ),
                1,
                output.attr("flush").get()
            )
        ).take();
        MatcherAssert.assertThat((byte) written.get(), Matchers.equalTo(data));
    }

    /**
     * Tests that PhOutput can close the output.
     */
    @Test
    public void closes() {
        final OutputStream output = new OutputStream() {
            private final AtomicBoolean closed = new AtomicBoolean(false);

            @Override
            public void write(final int data) throws IOException {
                if (this.closed.get()) {
                    throw new IOException();
                }
            }

            @Override
            public void close() {
                this.closed.set(true);
            }
        };
        new Dataized(
            new PhOutput(Phi.Φ, output).attr("close").get()
        ).take();
        Assertions.assertThrows(
            IOException.class,
            () -> output.write(0)
        );
    }
}
