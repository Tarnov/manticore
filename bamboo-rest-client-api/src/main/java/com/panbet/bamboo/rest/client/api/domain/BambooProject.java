package com.panbet.bamboo.rest.client.api.domain;

public class BambooProject {
    private String key;
    private String name;
    private String expand;
    private ProjectLink link;
    private PlanList plans;

    public BambooProject() {
    }

    public BambooProject(String key, String name, String expand, ProjectLink link, PlanList plans) {
        this.key = key;
        this.name = name;
        this.expand = expand;
        this.link = link;
        this.plans = plans;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public ProjectLink getLink() {
        return link;
    }

    public void setLink(ProjectLink link) {
        this.link = link;
    }

    public PlanList getPlans() {
        return plans;
    }

    public void setPlans(PlanList plans) {
        this.plans = plans;
    }
}
