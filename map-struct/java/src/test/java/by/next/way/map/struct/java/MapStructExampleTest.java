package by.next.way.map.struct.java;

import by.next.way.map.struct.java.SourceTargetMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapStructExampleTest {

  private SourceTargetMapper sourceTargetMapper = new SourceTargetMapperImpl();

  @Test
  public void shouldApplyConversions() {
    Source source = new Source();
    source.setFoo(42);
    source.setBar(23L);
    source.setZip(73);

    Target target = sourceTargetMapper.sourceToTarget(source);

    Assertions.assertNotNull(target);
    Assertions.assertEquals(42L, (long) target.getFoo());
    Assertions.assertEquals(23, target.getBar());
    Assertions.assertEquals("73", target.getZip());
  }

  @Test
  public void shouldHandleNulls() {
    Source source = new Source();
    Target target = sourceTargetMapper.sourceToTarget(source);
    Assertions.assertNotNull(target);
    Assertions.assertEquals(0L, (long) target.getFoo());
    Assertions.assertEquals(0, target.getBar());
    Assertions.assertEquals("0", target.getZip());
  }

  @Test
  public void shouldApplyConversionsToMappedProperties() {
    Source source = new Source();
    source.setQax(42);
    source.setBaz(23L);

    Target target = sourceTargetMapper.sourceToTarget(source);

    Assertions.assertNotNull(target);
    Assertions.assertEquals(42, (long) target.getBaz());
    Assertions.assertEquals(23, target.getQax());
  }

  @Test
  public void shouldApplyConversionsForReverseMapping() {
    Target target = new Target();
    target.setFoo(42L);
    target.setBar(23);
    target.setZip("73");

    Source source = sourceTargetMapper.targetToSource(target);

    Assertions.assertNotNull(source);
    Assertions.assertEquals(42, (long) source.getFoo());
    Assertions.assertEquals(23, (long) source.getBar());
    Assertions.assertEquals(73, source.getZip());
  }

  @Test
  public void shouldApplyConversionsToMappedPropertiesForReverseMapping() {
    Target target = new Target();
    target.setQax(42);
    target.setBaz(23L);

    Source source = sourceTargetMapper.targetToSource(target);

    Assertions.assertNotNull(source);
    Assertions.assertEquals(42, (long) source.getBaz());
    Assertions.assertEquals(23, source.getQax());
  }
}
