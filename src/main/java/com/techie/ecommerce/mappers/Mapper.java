package com.techie.ecommerce.mappers;

public interface Mapper <A,B>{

    B mapTo(A a);

    A mapFrom(B b);
}
