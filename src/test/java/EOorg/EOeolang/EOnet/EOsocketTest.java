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

import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Sockets tests.
 *
 * @since 0.0.0
 */
public final class EOsocketTest {

    /**
     * Integration test.
     *
     * Tests that socket can connect, listen, accept, write, and read data.
     */
    @Test
    public void echo() {
        final Phi socket = new PhWith(
            new PhWith(
                new EOsocket(Phi.Φ),
                "address",
                new Data.ToPhi("localhost")
            ),
            "port",
            new Data.ToPhi(8080L)
        );
        final Phi server = socket.attr("listen").get();
        new Thread(
            () -> {
                final Phi connection = server.attr("accept").get();
                new Dataized(
                    new PhWith(
                        connection
                            .attr("as-output").get()
                            .attr("write").get(),
                        "data",
                        connection
                            .attr("as-input").get()
                            .attr("read").get()
                    )
                ).take();
            }
        ).start();
        final byte data = 34;
        final Phi connection = socket.attr("connect").get();
        new Dataized(
            new PhWith(
                connection
                    .attr("as-output").get()
                    .attr("write").get(),
                "data",
                new Data.ToPhi((long) data)
            )
        ).take();
        final byte response = new Dataized(
            connection
                .attr("as-input").get()
                .attr("read").get()
        ).take(Long.class).byteValue();
        new Dataized(
            connection.attr("close").get()
        ).take();
        MatcherAssert.assertThat(
            response,
            Matchers.equalTo(data)
        );
    }
}
