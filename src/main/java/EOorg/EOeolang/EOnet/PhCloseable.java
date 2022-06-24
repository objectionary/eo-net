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

import java.io.Closeable;
import org.eolang.AtComposite;
import org.eolang.Data;
import org.eolang.PhDefault;
import org.eolang.Phi;

/**
 * Closable Phi.
 * Has target attribute of type Closeable, and close method that closes the target
 *
 * @since 0.0.0
 */
public final class PhCloseable extends PhDefault {

    /**
     * Ctor.
     *
     * @param sigma Parent.
     * @param target The Closeable.
     */
    public PhCloseable(final Phi sigma, final Closeable target) {
        super(sigma);
        this.add(
            "φ",
            new AtComposite(
                this,
                rho -> rho.attr("σ").get()
            )
        );
        this.add(
            "close",
            new AtComposite(
                this,
                rho -> {
                    target.close();
                    return new Data.ToPhi(true);
                }
            )
        );
    }
}
