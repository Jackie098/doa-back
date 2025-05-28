package project.common.mappers;

import project.common.config.Beans;
import project.common.database.Pageable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ApplicationScoped
public class GenericMapper {

  @Inject
  @NonNull
  ModelMapper mapper;

  public static GenericMapper getInstance() {
    return new GenericMapper(new Beans().modelMapper());
  }

  @SuppressWarnings("unchecked")
  public <T> T toObject(Object obj, Class<T> clazz) {
    if (Objects.isNull(obj))
      return null;
    if (obj.getClass().equals(clazz))
      return (T) obj;
    return mapper.map(obj, clazz);
  }

  public <T> List<T> toList(List<?> list, Class<T> clazz) {
    if (Objects.isNull(list) || list.isEmpty())
      return Collections.emptyList();
    return list.stream().map(obj -> toObject(obj, clazz)).collect(Collectors.toList());
  }

  public <T> Pageable<T> toPageable(Pageable<?> pageable, Class<T> clazz) {
    if (Objects.isNull(pageable))
      return Pageable.<T>builder().build();
    List<T> mappedPage = pageable.getPage().stream().map(obj -> toObject(obj, clazz)).collect(Collectors.toList());
    return Pageable.<T>builder()
        .page(mappedPage)
        .pageSize(pageable.getPageSize())
        .totalElements(pageable.getTotalElements())
        .build();
  }

}
