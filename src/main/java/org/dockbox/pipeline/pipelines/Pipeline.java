/*
 * Copyright (C) 2020 Guus Lieben
 *
 * This framework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see {@literal<http://www.gnu.org/licenses/>}.
 */

package org.dockbox.pipeline.pipelines;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.dockbox.hartshorn.core.Exceptional;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.dockbox.pipeline.pipes.IPipe;

public class Pipeline<I> extends AbstractPipeline<I, I> {

    /**
     * Processes an input by first wrapping it in an {@link Exceptional} and calling {@link
     * Pipeline#process(Exceptional)} on it.
     *
     * @param input The non-null {@code I} input value
     * @param throwable The nullable input {@link Throwable}
     *
     * @return An {@link Exceptional} containing the output. If the output is not present it will contain a throwable describing why
     */
    @Override
    public Exceptional<I> process(@NonNull final I input, @Nullable final Throwable throwable) {
        final Exceptional<I> exceptionalInput = Exceptional.of(input, throwable);

        return this.process(exceptionalInput);
    }

    /**
     * Processes an {@link Exceptional input} by calling {@link AbstractPipeline#processPipe(IPipe,
     * Exceptional)} on each {@link IPipe} in the pipeline and then returns the output wrapped in an
     * {@link Exceptional}.
     *
     * @param exceptionalInput A non-null {@link Exceptional} which contains the input value and throwable
     *
     * @return An {@link Exceptional} containing the output after it has been processed by the pipeline
     */
    @Override
    protected Exceptional<I> process(@NonNull Exceptional<I> exceptionalInput) {
        for (final IPipe<I, I> pipe : this.pipes()) {
            exceptionalInput = super.processPipe(pipe, exceptionalInput);

            // If the pipelines been cancelled, stop processing any further pipes.
            if (this.cancelled()) {
                // Reset it straight after it's been detected for next time the pipeline's used.
                this.permit();
                return Exceptional.of(
                        (I) super.cancelBehaviour().act(exceptionalInput.orNull()),
                        exceptionalInput.unsafeError()
                );
            }
        }

        return exceptionalInput;
    }
}
