package com.sax;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    String id;
    String departureStation;
    String arrivalStation;
    LocalTime departureTime;
    LocalTime arrivalTime;
}
