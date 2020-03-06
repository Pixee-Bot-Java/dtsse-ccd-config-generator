package uk.gov.hmcts.ccd.sdk.generator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import uk.gov.hmcts.ccd.sdk.JsonUtils;
import uk.gov.hmcts.ccd.sdk.types.HasLabel;

public class FixedListGenerator {

  public static void generate(File root, Map<Class, Integer> types) {
    File dir = root.toPath().resolve("FixedLists").toFile();
    dir.mkdir();

    for (Class c : types.keySet()) {
      if (c.isEnum()) {
        List<Map<String, Object>> fields = Lists.newArrayList();

        int order = 1;
        for (Object enumConstant : c.getEnumConstants()) {
          Map<String, Object> value = Maps.newHashMap();
          fields.add(value);
          value.put("LiveFrom", "01/01/2017");
          value.put("ID", c.getSimpleName());
          value.put("ListElementCode", enumConstant);
          if (enumConstant instanceof HasLabel) {
            value.put("ListElement", ((HasLabel) enumConstant).getLabel());
          } else {
            value.put("ListElement", enumConstant);
          }
          value.put("DisplayOrder", order++);
        }

        Path path = Paths.get(dir.getPath(), c.getSimpleName() + ".json");
        JsonUtils.mergeInto(path, fields, "ListElementCode");
      }
    }
  }
}
