package com.mahyaddin.task.domain.idClasses;

import com.mahyaddin.task.domain.enums.addressType;
import java.io.Serializable;

public class PersonAddressKey implements Serializable {
    private addressType addressType;
    private Integer person;
}
