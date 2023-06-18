package com.collectionlogplus;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class ItemData implements Serializable
{
    int collectionLogCount;
    int storedCount;
    int addedCount;
}
