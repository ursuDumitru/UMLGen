package org.uml;

import org.main.Config;

/**
 * Responsibility:
 * convert FQCNs/type strings into display format:
 *      if show-package=yes -> full name(with package)
 *      else -> name without package
*/
 public class NameFormatter {

    public String formatClassName(String fqcn, Config config) {
        if (fqcn == null) { return ""; }
        if (config.isShowPacket()) {
            return fqcn;
        }

        return cutPackageFromName(fqcn);
    }

    public String formatType(String typeString, Config config) {
        if (typeString == null) { return ""; }

        String simplifiedType = new String("");
        if (config.isShowPacket()) {
            simplifiedType = typeString;
        } else  {
            StringBuilder sb = new StringBuilder();
            StringBuilder token = new StringBuilder();

            for(char ch : typeString.toCharArray()) {
                if (isIdentifierChar(ch)) {
                    token.append(ch);
                } else {
                    sb.append(simplifyToken(token.toString()));
                    token.setLength(0);
                    sb.append(ch);
                }
            }

            sb.append(simplifyToken(token.toString()));
            simplifiedType = sb.toString();
        }
        simplifiedType = simplifiedType.replace("<", " of ")
                .replace(">", "")
                .replace(", ", ",")
                .replace("  ", " ")
                .replace("[]", " array");

        return simplifiedType;
    }

//    public String formatType(String typeString, Config config) {
//        if (typeString == null) { return ""; }
//        if (config.isShowPacket()) {
//            System.out.println("\n[DEBUG] formatType_1: " + typeString);
//            return typeString;
//        }
//
//        StringBuilder sb = new StringBuilder();
//        StringBuilder token = new StringBuilder();
//
//        for(char ch : typeString.toCharArray()) {
//            if (isIdentifierChar(ch)) {
//                token.append(ch);
//            } else {
//                sb.append(simplifyToken(token.toString()));
//                token = new StringBuilder();
//                sb.append(ch);
//            }
//        }
//
//        sb.append(simplifyToken(token.toString()));
//        String simplified = sb.toString();
//        simplified = simplified.replace("<", " of ");
//        simplified = simplified.replace(">", "");
//        simplified = simplified.replace(", ", ",");
//        simplified = simplified.replace("  ", " ");
//        System.out.println("\n[DEBUG] formatType_2: " + simplified);
//
//        return simplified;
//    }

    private String simplifyToken(String token) {
        if (token.isEmpty()) { return ""; }
        if (token.contains(".")) {
            int index = token.lastIndexOf('.');
            if (index == -1) { return token; }
            return token.substring(index+1);
        }
        return token;
    }

    private boolean isIdentifierChar(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_' ||  ch == '.' || ch == '$';
    }

    private String cutPackageFromName(String fqcn) {
        return fqcn.subSequence(fqcn.lastIndexOf('.') + 1, fqcn.length()).toString();
    }
 }
