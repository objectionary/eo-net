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

package EOorg.EOeolang.EOnet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eolang.Dataized;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * PhInput tests.
 *
 * @since 0.0.0
 */
public final class PhInputTest {

    /**
     * Tests that PhInput can read data.
     */
    @Test
    public void reads() {
        MatcherAssert.assertThat(
            new Dataized(
                new PhInput(
                    Phi.Φ,
                    new ByteArrayInputStream("φ".getBytes())
                ).attr("read").get()
            ).take(Long.class).byteValue(),
            Matchers.equalTo("φ".getBytes()[0])
        );
    }

    /**
     * Tests that PhInput can close the stream.
     */
    @Test
    public void closes() {
        final InputStream stream = new InputStream() {
            private final AtomicBoolean closed = new AtomicBoolean(false);

            @Override
            public int read() throws IOException {
                if (this.closed.get()) {
                    throw new IOException();
                }
                return 0;
            }

            @Override
            public void close() {
                this.closed.set(true);
            }
        };
        new Dataized(
            new PhInput(Phi.Φ, stream)
                .attr("close").get()
        ).take();
        Assertions.assertThrows(IOException.class, stream::read);
    }
}
