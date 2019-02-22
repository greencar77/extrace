package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Domain;

public class HtmlPublisher extends Publisher {

    public void publishDomainClasses(Domain domain, String path) {
        StringBuilder sb = new StringBuilder();
        domain.getClasses().values().stream()
                .sorted()
                .forEach(c -> {
                    sb.append("<a href=\"..\\classes\\" + toPath(c.getPackageX()) + "/" + c.getName() + ".txt" + "\">" + c.getFullName() + "</a>").append("<br/>").append("\n");
                });
        writeFileWrapper(path, sb);
    }
}
