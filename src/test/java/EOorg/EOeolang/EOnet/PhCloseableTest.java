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

import java.util.concurrent.atomic.AtomicBoolean;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * PhCloseable tests.
 *
 * @since 0.0.0
 */
public final class PhCloseableTest {

    /**
     * Tests that PhCloseable closes the underlying Closeable.
     */
    @Test
    public void closes() {
        final AtomicBoolean closed = new AtomicBoolean(false);
        new Dataized(
            new PhCloseable(
                Phi.Φ,
                () -> closed.set(true)
            ).attr("close").get()
        ).take();
        MatcherAssert.assertThat(
            closed.get(),
            Matchers.equalTo(true)
        );
    }

    /**
     * Tests that PhCloseable decorates the passed object.
     */
    @Test
    public void decorates() {
        MatcherAssert.assertThat(
            new Dataized(
                new PhCloseable(
                    new Data.ToPhi("abc"),
                    null
                )
            ).take(String.class),
            Matchers.equalTo("abc")
        );
    }
}
