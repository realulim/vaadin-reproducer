package de.mayring.reproducer.authorization;

import org.ilay.NavigationAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NavigationAnnotation(AccessControlEvaluator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Protected {
    AccessControl value();
}
