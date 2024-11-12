package com.panbet.bamboo.rest.client.api.domain;


import com.atlassian.util.concurrent.Promise;

import java.util.List;


public interface ResultRestClient {
    Promise<BuildResult> getLatestBuildResult(String buildKey, String planBranch);

    /**
     * Получение данных по урлу /rest/api/1.0/result/{buildKey}-{planBranch}
     *
     * @param buildKey - ключ проекта. Пример: PANBUILD
     * @param planBranch   - ключ билда с номером. Пример: PANBETMASTER-361
     * @return BuildResult
     */
    Promise<BuildResult> getBuildResult(String buildKey, String planBranch);

    Promise<List<BuildResult>> getLatestBuildsResults(String buildKey, String planBranch, int limit);

    /**
     * Получаем все последние билды, которые имею статус Success(билды прошли успешно)
     */
    Promise<List<BuildResult>> getLatestSuccessBuildsResults(String buildKey, String planBranch, int limit);
}
