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

import org.dockbox.pipeline.pipelines.AbstractPipeline;

/**
 * An interface for a pipe. This interface is used to define a pipe, which is the basic building block
 * of a {@link AbstractPipeline pipeline}.
 * @param <I> The input type of the pipe.
 * @param <O> The output type of the pipe.
 */
public interface IPipe<I, O> {

    /**
     * If this method is overridden, you can then call it to get the {@link Class} of the pipe, even
     * when created by a lambda expression.
     *
     * @return The {@link Class} of the pipe.
     */
    default Class<? extends IPipe> type() {
        return IPipe.class;
    }
}
