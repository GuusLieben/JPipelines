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

package org.dockbox.pipeline.pipes;

import org.dockbox.hartshorn.core.ApplicationException;
import org.dockbox.hartshorn.core.Exceptional;

@FunctionalInterface
public interface Pipe<I, O> extends StandardPipe<I, O> {

    static <I, O> Pipe<I, O> of(final Pipe<I, O> pipe) {
        return pipe;
    }

    @Override
    default O apply(final Exceptional<I> input) throws ApplicationException {
        return this.execute(input.orNull(), input.unsafeError());
    }

    O execute(I input, Throwable throwable) throws ApplicationException;
}
