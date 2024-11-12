package com.panbet.stash.rest.client.api.alternative.commit;


public interface MinimalAuthor {
    /**
     * @return Если автор активен - возвращает логин, иначе - полное имя(displayName) или e-mail(emailAddress)
     * в таком виде все выдается рест клиентом
     */
    String getName();

    /**
     * @return Если автор не активен - rest service вообще не дает это поле. В этом случае возвращаем пустую строку
     */
    String getEmailAddress();

    boolean isActive();

    /**
     * @throws IllegalStateException если пользователь не активен
     */
    Author getAuthor();
}
