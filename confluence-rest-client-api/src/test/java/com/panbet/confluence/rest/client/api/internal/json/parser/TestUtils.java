package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.content.*;
import com.atlassian.confluence.api.model.pagination.PageResponse;
import com.atlassian.confluence.api.model.people.Person;
import com.atlassian.confluence.api.model.web.Icon;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;


public class TestUtils {
    private static final String RESULTS_JSON = "{\n" +
            "      results: [\n" +
            "        {\n" +
            "          id: \"1\",\n" +
            "          type: \"page\",\n" +
            "          title: \"result1\"\n" +
            "        },\n" +
            "        {\n" +
            "          id: \"2\",\n" +
            "          type: \"page\",\n" +
            "          title: \"result2\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }";

    private static final String ICON_JSON = "{\n" +
            "path: \"/test\",\n" +
            "width: 48,\n" +
            "height: 48,\n" +
            "isDefault: true\n" +
            "}";

    private static final String ANONYMOUS_PERSON_JSON = "{\n" +
            "type: \"anonymous\",\n" +
            "profilePicture: " +
            ICON_JSON +
            "\n" +
            "}";

    private static final String KNOWN_PERSON_JSON = "{\n" +
            "type: \"known\",\n" +
            "profilePicture: " +
            ICON_JSON +
            ",\n" +
            "username: \"testName\",\n" +
            "displayName: \"testDispName\"\n" +
            "}";

    private static final String VERSION_JSON = "{\n" +
            "by: " +
            KNOWN_PERSON_JSON +
            ",\n" +
            "when: \"2011-11-03T15:17:55.000+0400\",\n" +
            "message: \"test\",\n" +
            "number: 1,\n" +
            "minorEdit: false\n" +
            "}";

    private static final String HISTORY_JSON = "{\n" +
            "lastUpdated: " +
            VERSION_JSON +
            ",\n" +
            "latest: true,\n" +
            "createdBy: " +
            KNOWN_PERSON_JSON +
            ",\n" +
            "createdDate: \"2011-11-03T15:17:55.000+0400\",\n" +
            "}";

    private static final String SPACE_JSON = "{\n" +
            "id: 98305,\n" +
            "key: \"ds\",\n" +
            "name: \"testName\",\n" +
            "icon: " + ICON_JSON +
            ",\n" +
            "description: {\n" +
            "plain: {\n" +
            "value: \"testVal1\",\n" +
            "representation: \"plain\"\n" +
            "},\n" +
            "view: {\n" +
            "value: \"testVal2\",\n" +
            "representation: \"view\"\n" +
            "}\n" +
            "},\n" +
            "type: \"global\",\n" +
            "}";

    private static final String CONTENT_JSON = "{\n" +
            "  id: \"12345\",\n" +
            "  type: \"page\",\n" +
            "  title: \"test\",\n" +
            "  space: " +
            SPACE_JSON +
            ",\n" +
            "  history: " +
            HISTORY_JSON +
            ",\n" +
            "  version: " +
            VERSION_JSON +
            ",\n" +
            "  ancestors: [\n" +
            "    {\n" +
            "      id: \"5\",\n" +
            "      type: \"page\",\n" +
            "      title: \"ancestor\"\n" +
            "    }\n" + "  ],\n" +
            "  children: {\n" +
            "    page: " +
            RESULTS_JSON +
            ",\n" +
            "    comment: " +
            RESULTS_JSON +
            "\n" +
            "  },\n" +
            "  descendants: {\n" +
            "    page: " +
            RESULTS_JSON +
            ",\n" +
            "    comment: " +
            RESULTS_JSON +
            "\n" +
            "  },\n" +
            "  container: " +
            SPACE_JSON +
            ",\n" +
            "  body: {\n" +
            "    storage: {\n" +
            "      value: \"test1\",\n" +
            "      representation: \"storage\",\n" +
            "    },\n" +
            "    editor: {\n" +
            "      value: \"test2\",\n" +
            "      representation: \"editor\",\n" +
            "    }\n" +
            "  }\n" +
            "}";


    public static void assertForContent(Content content) {
        assertEquals(12345, content.getId().asLong());
        assertEquals(ContentType.PAGE, content.getType());
        assertEquals("test", content.getTitle());
        assertForSpace(content.getSpaceRef().get());
        assertForHistory(content.getHistory());
        assertForVersion(content.getVersion());

        assertEquals(1, content.getAncestors().size());
        Content ancestor = content.getAncestors().get(0);
        assertEquals(5, ancestor.getId().asLong());
        assertEquals(ContentType.PAGE, ancestor.getType());
        assertEquals("ancestor", ancestor.getTitle());

        assertForTypeResponseMap(content.getChildren());
        assertForTypeResponseMap(content.getDescendants());

        assertForContainer(content.getContainer());
        assertForContentBody(content.getBody());
    }


    private static void assertForContentBody(Map<ContentRepresentation, ContentBody> body) {
        Set<ContentRepresentation> set = new HashSet<>();
        set.add(ContentRepresentation.STORAGE);
        set.add(ContentRepresentation.EDITOR);
        assertEquals(set, body.keySet());
        ContentBody contentBody1 = body.get(ContentRepresentation.STORAGE);
        ContentBody contentBody2 = body.get(ContentRepresentation.EDITOR);
        assertEquals(contentBody1.getRepresentation(), ContentRepresentation.STORAGE);
        assertEquals(contentBody1.getValue(), "test1");
        assertEquals(contentBody2.getRepresentation(), ContentRepresentation.EDITOR);
        assertEquals(contentBody2.getValue(), "test2");
    }


    private static void assertForContainer(Container container) {
        if (container instanceof Space) {
            assertForSpace((Space) container);
        }
    }


    private static void assertForTypeResponseMap(Map<ContentType, PageResponse<Content>> map) {
        Map<ContentType, PageResponse<Content>> typeResponseMap = new HashMap<>();
        typeResponseMap.put(ContentType.PAGE, null);
        typeResponseMap.put(ContentType.COMMENT, null);

        assertEquals(typeResponseMap.keySet(), map.keySet());
        for (ContentType type : typeResponseMap.keySet()) {
            assertEquals(2, map.get(type).size());
            Content content1 = map.get(type).getResults().get(0);
            Content content2 = map.get(type).getResults().get(1);
            assertEquals(1, content1.getId().asLong());
            assertEquals(ContentType.PAGE, content1.getType());
            assertEquals("result1", content1.getTitle());
            assertEquals(2, content2.getId().asLong());
            assertEquals(ContentType.PAGE, content2.getType());
            assertEquals("result2", content2.getTitle());
        }
    }


    public static void assertForIcon(Icon icon) {
        assertEquals("/test", icon.getPath());
        assertEquals(48, icon.getWidth());
        assertEquals(48, icon.getHeight());
        assertTrue(icon.getIsDefault());
    }


    public static void assertForAnonymous(Person person) {
        assertEquals("Anonymous", person.getDisplayName());

        assertForIcon(person.getProfilePicture());
    }


    public static void assertForKnown(Person person) {
        assertEquals("testDispName", person.getDisplayName());
        assertEquals("testName", person.getOptionalUsername().get());

        assertForIcon(person.getProfilePicture());
    }


    public static void assertForVersion(Version version) {
        assertForKnown(version.getBy());
        assertEquals(new DateTime("2011-11-03T15:17:55.000+0400"), version.getWhen());
        assertEquals("test", version.getMessage());
        assertEquals(1, version.getNumber());
        assertFalse(version.isMinorEdit());
    }


    public static void assertForHistory(History history) {
        assertForVersion(history.getLastUpdatedRef().get());
        assertNull(history.getPreviousVersionRef().get());
        assertNull(history.getNextVersionRef().get());
        assertForKnown(history.getCreatedBy());
        assertTrue(history.isLatest());
        assertEquals(new DateTime("2011-11-03T15:17:55.000+0400"), history.getCreatedDate());
    }


    public static void assertForSpace(Space space) {
        assertEquals(98305, space.getId());
        assertEquals("ds", space.getKey());
        assertEquals("testName", space.getName());
        assertForIcon(space.getIconRef().get());

        Map<ContentRepresentation, FormattedBody> descr = new HashMap<>();
        descr.put(ContentRepresentation.PLAIN, new FormattedBody(ContentRepresentation.PLAIN, "testVal1"));
        descr.put(ContentRepresentation.VIEW, new FormattedBody(ContentRepresentation.VIEW, "testVal2"));

        assertEquals(descr.keySet(), space.getDescription().keySet());
        for (ContentRepresentation repr : descr.keySet()) {
            assertEquals(descr.get(repr).getRepresentation(), space.getDescription().get(repr).getRepresentation());
            assertEquals(descr.get(repr).getValue(), space.getDescription().get(repr).getValue());
        }

        assertEquals(SpaceType.GLOBAL, space.getType());
    }


    public static String getIconJson() {
        return ICON_JSON;
    }


    public static String getAnonymousPersonJson() {
        return ANONYMOUS_PERSON_JSON;
    }


    public static String getKnownPersonJson() {
        return KNOWN_PERSON_JSON;
    }


    public static String getVersionJson() {
        return VERSION_JSON;
    }


    public static String getHistoryJson() {
        return HISTORY_JSON;
    }


    public static String getSpaceJson() {
        return SPACE_JSON;
    }


    public static String getContentJson() {
        return CONTENT_JSON;
    }
}
