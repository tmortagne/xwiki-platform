/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.macro.python;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.script.ScriptContext;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.internal.macro.polyglot.AbstractPolyglotMacro;
import org.xwiki.rendering.macro.descriptor.DefaultContentDescriptor;
import org.xwiki.rendering.macro.polyglot.PolyglotScriptMacroParameters;

/**
 * A macro for executing Python 3 scripts.
 * 
 * @version $Id$
 */
@Component
@Named("python3")
@Singleton
public class Python3Macro extends AbstractPolyglotMacro<PolyglotScriptMacroParameters>
{
    /**
     * The description of the macro.
     */
    private static final String DESCRIPTION = "Executes a Python 3 script.";

    /**
     * The description of the macro content.
     */
    private static final String CONTENT_DESCRIPTION = "The Python 3 script to execute";

    /**
     * Create and initialize the descriptor of the macro.
     */
    public Python3Macro()
    {
        super("Python 3", DESCRIPTION, new DefaultContentDescriptor(CONTENT_DESCRIPTION));
    }

    @Override
    protected String getScriptEngineName()
    {
        return "python";
    }

    @Override
    protected void uploadBindings(ScriptContext scriptContext, Context pContext)
    {
        super.uploadBindings(scriptContext, pContext);

        // Also inject global Python variables
        Value globals = pContext.eval(getScriptEngineName(), "globals()");
        Value keys = globals.getHashKeysIterator();
        while (keys.hasIteratorNextElement()) {
            String name = keys.getIteratorNextElement().asString();
            if (!name.startsWith("__")) {
                Value value = globals.getHashValue(name);
                // Skip what can't safely outlive the context (see below)
                //if (!value.canExecute() && !value.canInstantiate()) {
                    scriptContext.setAttribute(name, value.as(Object.class), ScriptContext.ENGINE_SCOPE);
                //}
            }
        }
        
        /*
        for (String key : globals.getMemberKeys()) {
            if (!key.startsWith("__")) {
                Value value = globals.getMember(key);
                if (!value.canExecute() && !value.canInstantiate()) {
                    setAttribute(scriptContext, key, value.as(Object.class));
                }
            }
        }*/
    }
}
