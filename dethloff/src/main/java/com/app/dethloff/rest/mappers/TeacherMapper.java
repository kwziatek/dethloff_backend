package com.app.dethloff.rest.mappers;

import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PeselMapper.class, PhoneNumberMapper.class})
public interface TeacherMapper {

    //For creation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "phoneNumber", qualifiedByName = "toBasic")
    @Mapping(target = "companyPhoneNumber", qualifiedByName = "toBasic")
    TeacherEntity requestToEntity(TeacherRequestDTO request);

    //For update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "phoneNumber", qualifiedByName = "toBasic")
    @Mapping(target = "companyPhoneNumber", qualifiedByName = "toBasic")
    void updateEntityFromRequest(TeacherRequestDTO request, @MappingTarget TeacherEntity entity);

    @Mapping(target = "phoneNumber", qualifiedByName = "toExtended")
    @Mapping(target = "companyPhoneNumber", qualifiedByName = "toExtended")
    TeacherResponseDTO entityToResponse(TeacherEntity entity);
}
