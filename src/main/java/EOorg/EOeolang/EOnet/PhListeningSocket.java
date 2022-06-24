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

import java.net.ServerSocket;
import org.eolang.AtComposite;
import org.eolang.ExFailure;
import org.eolang.PhDefault;
import org.eolang.Phi;

/**
 * Listening socket.
 *
 * @since 0.0.0
 */
public final class PhListeningSocket extends PhDefault {

    /**
     * Ctor.
     *
     * @param sigma Original socket.
     * @param server Server.
     */
    public PhListeningSocket(final Phi sigma, final ServerSocket server) {
        super(sigma);
        this.add(
            "φ",
            new AtComposite(
                this,
                rho -> new PhCloseable(rho.attr("σ").get(), server)
            )
        );
        this.add(
            "accept",
            new AtComposite(
                this,
                rho -> new PhConnectedSocket(rho.copy(), server.accept())
            )
        );
        this.add(
            "listen",
            new AtComposite(
                this,
                rho -> {
                    throw new ExFailure(
                        "Already listening."
                    );
                }
            )
        );
        this.add(
            "connect",
            new AtComposite(
                this,
                rho -> {
                    throw new ExFailure(
                        "This socket is listening, and cannot be connected."
                    );
                }
            )
        );
    }
}
