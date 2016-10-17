package com.boxtrotstudio.fishing.utils.annotations;

import java.lang.annotation.*;

/**
 * Indicates that an element is to be used by the ghost engine only and not by third-party programs
 */
@Documented
@InternalOnly
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE})
public @interface InternalOnly {
}
