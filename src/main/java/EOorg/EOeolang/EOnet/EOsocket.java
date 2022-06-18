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

import EOorg.EOeolang.EOtxt.EOsprintf;
import org.eolang.*;
import org.eolang.net.PhConnectedSocket;
import org.eolang.net.PhListeningSocket;

import java.net.*;

public final class EOsocket extends PhDefault {
    public EOsocket(final Phi sigma) {
        super(sigma);
        this.add("address", new AtFree());
        this.add("port", new AtFree());
        this.add(
            "φ",
            new AtComposite(
                this,
                rho -> new PhWith(
                    new PhWith(
                        new PhWith(
                            new EOsprintf(rho),
                            0,
                            new Data.ToPhi("%s:%d")
                        ),
                        1,
                        rho.attr("address").get()
                    ),
                    2,
                    rho.attr("port").get()
                )
            )
        );
        this.add(
            "connect",
            new AtComposite(
                this,
                rho -> {
                    final Socket connection = new Socket();
                    connection.connect(address(rho));
                    return new PhConnectedSocket(rho.copy(), connection);
                }
            )
        );
        this.add(
            "listen",
            new AtComposite(
                this,
                rho -> {
                    final ServerSocket server = new ServerSocket();
                    server.bind(address(rho));
                    return new PhListeningSocket(rho.copy(), server);
                }
            )
        );
    }

    /**
     * Parses InetSocketAddress from EOsocket
     */
    private static InetSocketAddress address(Phi socket) throws UnknownHostException {
        return new InetSocketAddress(
            InetAddress.getByName(
                new Param(socket, "address").strong(String.class)
            ),
            new Param(socket, "port").strong(Long.class).intValue()
        );
    }
}
