package project.v1.dtos.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.Mask;
import project.common.annotations.enums.MaskType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {
  private Long id;
  private String name;
  private String email;

  @Mask(MaskType.PHONE)
  private String phoneNumber;
  @Mask(MaskType.CPF)
  private String document;
}
