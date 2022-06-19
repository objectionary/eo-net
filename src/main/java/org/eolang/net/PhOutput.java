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

package org.eolang.net;

import java.io.OutputStream;
import org.eolang.AtComposite;
import org.eolang.AtFree;
import org.eolang.Data;
import org.eolang.Param;
import org.eolang.PhDefault;
import org.eolang.Phi;

/**
 * Input.
 *
 * @since 0.0.0
 */
public final class PhOutput extends PhDefault {

    /**
     * Ctor.
     *
     * @param sigma Parent.
     * @param stream Stream.
     */
    public PhOutput(final Phi sigma, final OutputStream stream) {
        super(sigma);
        this.add(
            "φ",
            new AtComposite(
                this,
                rho -> new PhCloseable(rho, stream)
            )
        );
        this.add(
            "write",
            new AtComposite(
                this,
                rho -> new Write(rho, stream)
            )
        );
        this.add(
            "flush",
            new AtComposite(
                this,
                rho -> {
                    stream.flush();
                    return new Data.ToPhi(true);
                }
            )
        );
    }

    /**
     * Write.
     *
     * @since 0.0.0
     */
    private static final class Write extends PhDefault {

        /**
         * Ctor.
         *
         * @param sigma Parent.
         * @param stream Stream.
         */
        Write(final Phi sigma, final OutputStream stream) {
            super(sigma);
            this.add("data", new AtFree());
            this.add(
                "φ",
                new AtComposite(
                    this,
                    rho -> {
                        stream.write(
                            new Param(rho, "data")
                                .strong(Long.class)
                                .byteValue()
                        );
                        return new Data.ToPhi(true);
                    }
                )
            );
        }
    }
}
