package com.panbet.confluence.rest.client.api.internal.json;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.api.model.reference.Reference;
import com.atlassian.confluence.api.model.web.Icon;
import com.panbet.confluence.rest.client.api.internal.json.generator.ConfluenceContentJsonGenerator;
import com.panbet.confluence.rest.client.api.internal.json.generator.ConfluenceIconJsonGenerator;
import com.panbet.confluence.rest.client.api.internal.json.generator.ConfluenceSpaceJsonGenerator;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class ReferenceSerializer {
    private static final ConfluenceContentJsonGenerator contentJsonGenerator = new ConfluenceContentJsonGenerator();

    private static final ConfluenceIconJsonGenerator iconJsonGenerator = new ConfluenceIconJsonGenerator();

    private static final ConfluenceSpaceJsonGenerator spaceJsonGenerator = new ConfluenceSpaceJsonGenerator();


    public void serializeContentReference(final JSONObject jsonObject, final Reference<Content> ref, final String key)
            throws JSONException {
        if (ref.isExpanded()) {
            Content content = ref.get();
            if (content != null) {
                jsonObject.put(key, contentJsonGenerator.generate(content));
            }
        } else {
            jsonObject.put(key, new JSONObject().put("id", Content.getContentId(ref).asLong()));
        }
    }


    public void serializeSpaceReference(final JSONObject jsonObject, final Reference<Space> ref, final String key)
            throws JSONException {
        if (ref.isExpanded()) {
            Space space = ref.get();
            if (space != null) {
                jsonObject.put("space", spaceJsonGenerator.generate(space));
            }
        } else {
            jsonObject.put(key, new JSONObject().put("key", Space.getSpaceKey(ref)));
        }
    }


    public void serializeIconReference(final JSONObject jsonObject, final Reference<Icon> ref, final String key)
            throws JSONException {
        if (ref.isExpanded()) {
            Icon icon = ref.get();
            if (icon != null) {
                jsonObject.put(key, iconJsonGenerator.generate(icon));
            }
        }
    }

}
