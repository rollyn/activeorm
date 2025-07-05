package com.ethanium.activeorm.runtime;
public interface DirtyCheckStrategy{ boolean isDirty(Object e);
    DirtyCheckStrategy DEFAULT=e->true;}
