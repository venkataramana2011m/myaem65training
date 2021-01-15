/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.myaem65training.components.cardlayout;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class cardlayout_html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_clientlib = null;
Object _global_multiitems = null;
Collection var_collectionvar2_list_coerced$ = null;
_global_clientlib = renderContext.call("use", "/libs/granite/sightly/templates/clientlib.html", obj());
{
    Object var_templatevar0 = renderContext.getObjectModel().resolveProperty(_global_clientlib, "all");
    {
        String var_templateoptions1_field$_categories = "clientlib-cardlayout";
        {
            java.util.Map var_templateoptions1 = obj().with("categories", var_templateoptions1_field$_categories);
            callUnit(out, renderContext, var_templatevar0, var_templateoptions1);
        }
    }
}
out.write("\n<h2>This is your AEM HTML Template Language component with a Multifield</h2>\n");
_global_multiitems = renderContext.call("use", com.myaem65training.core.models.CardLayoutModel.class.getName(), obj());
out.write("<div class=\"card-body\">\n    ");
{
    Object var_collectionvar2 = renderContext.getObjectModel().resolveProperty(renderContext.getObjectModel().resolveProperty(_global_multiitems, "cards"), "listChildren");
    {
        long var_size3 = ((var_collectionvar2_list_coerced$ == null ? (var_collectionvar2_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar2)) : var_collectionvar2_list_coerced$).size());
        {
            boolean var_notempty4 = (var_size3 > 0);
            if (var_notempty4) {
                {
                    long var_end7 = var_size3;
                    {
                        boolean var_validstartstepend8 = (((0 < var_size3) && true) && (var_end7 > 0));
                        if (var_validstartstepend8) {
                            out.write("<section class=\"card-list\">");
                            if (var_collectionvar2_list_coerced$ == null) {
                                var_collectionvar2_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar2);
                            }
                            long var_index9 = 0;
                            for (Object head : var_collectionvar2_list_coerced$) {
                                {
                                    boolean var_traversal11 = (((var_index9 >= 0) && (var_index9 <= var_end7)) && true);
                                    if (var_traversal11) {
                                        out.write("\n\t\t<article class=\"card\">\n\t\t\t<header class=\"card-header\">\n\t\t\t\t<p>");
                                        {
                                            Object var_12 = renderContext.call("xss", renderContext.call("format", "yyyy-MM-dd HH:mm:ss.SSSXXX", obj().with("format", renderContext.getObjectModel().resolveProperty(head, "cardCreatedOn")).with("timezone", "UTC")), "text");
                                            out.write(renderContext.getObjectModel().toString(var_12));
                                        }
                                        out.write("</p>\n\t\t\t\t<a");
                                        {
                                            String var_attrcontent13 = (renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(head, "cardLink"), "uri")) + ".html");
                                            {
                                                Object var_shoulddisplayattr14 = ((renderContext.getObjectModel().toBoolean(var_attrcontent13) ? var_attrcontent13 : ("false".equals(var_attrcontent13))));
                                                if (renderContext.getObjectModel().toBoolean(var_shoulddisplayattr14)) {
                                                    out.write(" href=\"");
                                                    out.write(renderContext.getObjectModel().toString(var_attrcontent13));
                                                    out.write("\"");
                                                }
                                            }
                                        }
                                        out.write(" target=\"_blank\"><h2>");
                                        {
                                            Object var_15 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(head, "cardTitle"), "text");
                                            out.write(renderContext.getObjectModel().toString(var_15));
                                        }
                                        out.write("</h2></a>\n\t\t\t</header>");
                                        {
                                            String var_16 = (("\n            " + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(head, "cardDesc"), "text"))) + "\n\t\t\t");
                                            out.write(renderContext.getObjectModel().toString(var_16));
                                        }
                                        out.write("<div class=\"card-author\">\n\t\t\t\t<a class=\"author-avatar\" href=\"#\">\n\t\t\t\t  <img");
                                        {
                                            Object var_attrvalue17 = renderContext.getObjectModel().resolveProperty(head, "fileReference");
                                            {
                                                Object var_attrcontent18 = renderContext.call("xss", var_attrvalue17, "uri");
                                                {
                                                    Object var_shoulddisplayattr20 = ((renderContext.getObjectModel().toBoolean(var_attrcontent18) ? var_attrcontent18 : ("false".equals(var_attrvalue17))));
                                                    if (renderContext.getObjectModel().toBoolean(var_shoulddisplayattr20)) {
                                                        out.write(" src");
                                                        {
                                                            boolean var_istrueattr19 = (var_attrvalue17.equals(true));
                                                            if (!var_istrueattr19) {
                                                                out.write("=\"");
                                                                out.write(renderContext.getObjectModel().toString(var_attrcontent18));
                                                                out.write("\"");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        out.write("/>\n\t\t\t\t</a>\n\t\t\t\t<svg class=\"half-circle\" viewBox=\"0 0 106 57\">\n\t\t\t\t  <path d=\"M102 4c0 27.1-21.9 49-49 49S4 31.1 4 4\"></path>\n\t\t\t\t</svg>\n\t  \n\t\t\t\t<div class=\"author-name\">\n\t\t\t\t  <div class=\"author-name-prefix\">Author</div>");
                                        {
                                            String var_21 = (("\n\t\t\t\t  " + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(head, "cardAuthorName"), "text"))) + "\n\t\t\t\t");
                                            out.write(renderContext.getObjectModel().toString(var_21));
                                        }
                                        out.write("</div>\n\t\t\t  </div>\n\t\t\t  <div class=\"tags\">\n\t\t\t\t<a href=\"#\">html</a>\n\t\t\t\t<a href=\"#\">css</a>\n\t\t\t\t<a href=\"#\">web-dev</a>\n\t\t\t  </div>\n\t\t</article>\n   ");
                                    }
                                }
                                var_index9++;
                            }
                            out.write("</section>");
                        }
                    }
                }
            }
        }
    }
    var_collectionvar2_list_coerced$ = null;
}
out.write("\n</div>");


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

