package org.enigma.virtualmuseumvisitingplatform.service.abstracts;

import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;

public interface IUserService {
    Result login(UserLoginDto userLoginDto);
}
