package org.hsha.hsha.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThIterationCounter {

    int count;

    public int incrementAndGet() {
        return count++;
    }

}
