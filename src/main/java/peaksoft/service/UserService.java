package peaksoft.service;


import peaksoft.dto.user.SimpleResponse;
import peaksoft.dto.user.UserRequest;

import java.util.List;

public interface UserService {

   

    SimpleResponse deleteUser(Long id);

}
