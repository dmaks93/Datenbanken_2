package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.stud.entity.Category;
import java.util.Comparator;

public class CategoryComparator implements Comparator<Category> {
  @Override
  public int compare(Category cat1, Category cat2) {
    return cat1.getName().compareTo(cat2.getName());
  }
}
