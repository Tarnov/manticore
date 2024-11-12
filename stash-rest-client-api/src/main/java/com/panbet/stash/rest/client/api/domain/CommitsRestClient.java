package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.Diff;
import com.atlassian.stash.content.Path;
import com.atlassian.util.concurrent.Promise;
import com.panbet.stash.rest.client.api.alternative.commit.Commit;

import java.util.Collection;


public interface CommitsRestClient {
    /**
     * Возвращает коммит по {@code Id} из указанного проекта {@code project} и репозитория {@code repo}
     *
     * @param id      - id коммита
     * @param project - ключ проекта
     * @param repo    - ключ репозитория
     * @return коммит с {@code Id} из указанных проекта {@code project} и репозитория {@code repo}
     */
    Promise<Commit> getCommit(final String id, final String project, final String repo);

    /**
     * Возвращает скисок коммитов начиная с ветки {@code sinceBrunch} до ветки {@code untilBranch},
     * из указанного проекта {@code project} и репозитория {@code repo}
     *
     * @param sinceBrunch - ключ ветки с которой начинает собирать коммиты
     * @param untilBranch - ключ ветки на которой заканчиваем собирать коммиты
     * @param project     - ключ проекта
     * @param repo        - ключ репозитория
     * @return список коммитов
     */
    Promise<Collection<Commit>> getCommits(final String sinceBrunch, final String untilBranch,
                                           final String project, final String repo);

    /**
     * Возвращает скисок коммитов начиная с первого коммита до ветки/коммита {@code until},
     * из указанного проекта {@code project} и репозитория {@code repo}
     *
     * @param until   - ветка/коммит на котором/-ой заканчиваем собирать коммиты
     * @param project - ключ проекта
     * @param repo    - ключ репозитория
     * @param limit   - лимит получаемых коммитов, начиная с самого последнего(максимальный лимит 1000)
     * @return список коммитов
     */
    Promise<Collection<Commit>> getCommits(final String until, final String project, final String repo, int limit);

    Promise<Collection<Change>> getChanges(final String hash, final String project, final String repo);

    Promise<Collection<Diff>> getDiffsByPath(final String hash, final Path path, final String project,
                                             final String repo);
}
