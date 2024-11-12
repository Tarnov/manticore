package com.panbet.bamboo.rest.client.api.domain;

import java.util.ArrayList;
import java.util.List;

public class PlanList {
    private List<ProjectPlan> plans;

    public PlanList() {
    }

    public PlanList(int size) {
        this.plans = new ArrayList<>(size);
    }

    public Integer getSize() {
        return plans.size();
    }

    public List<ProjectPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<ProjectPlan> plans) {
        this.plans = plans;
    }
}
