package com.app.dethloff.rest.mappers;

import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PeselMapper.class, PhoneNumberMapper.class})
public interface StudentMapper {

    //For creation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "phoneNumber", qualifiedByName = "toBasic")
    @Mapping(target = "guardianPhoneNumber", qualifiedByName = "toBasic")
    @Mapping(target = "companyPhoneNumber", qualifiedByName = "toBasic")
    StudentEntity requestToEntity(StudentRequestDTO request);

    //For updating
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "phoneNumber", qualifiedByName = "toBasic")
    @Mapping(target = "guardianPhoneNumber", qualifiedByName = "toBasic")
    @Mapping(target = "companyPhoneNumber", qualifiedByName = "toBasic")
    void updateEntityFromRequest(StudentRequestDTO request, @MappingTarget StudentEntity entity);

    @Mapping(target = "phoneNumber", qualifiedByName = "toExtended")
    @Mapping(target = "guardianPhoneNumber", qualifiedByName = "toExtended")
    @Mapping(target = "companyPhoneNumber", qualifiedByName = "toExtended")
    StudentResponseDTO entityToResponse(StudentEntity entity);
}
