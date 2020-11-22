package br.com.burnhop.api.controller;

import br.com.burnhop.model.dto.CreatedGroupDto;
import br.com.burnhop.model.dto.GroupDto;
import br.com.burnhop.model.Groups;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.GroupsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Optional;

public class GroupsController {

    GroupsRepository groupsRepository;
    UsersRepository usersRepository;

    public GroupsController(GroupsRepository groupsRepository, UsersRepository usersRepository){
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
    }

    public GroupDto createGroup(CreatedGroupDto newGroup) throws NoSuchAlgorithmException {
        Optional<Users> user = usersRepository.findById((Integer) newGroup.getAdmin());
        if(user.isPresent()) {
            GroupDto gpname = getGroupByName(newGroup.getName());
            if (gpname == null) {
                Groups group = new Groups(newGroup.getName(), new Timestamp(System.currentTimeMillis()));
                group.setAdmin(user.get());
                groupsRepository.save(group);

                GroupDto groupcreated = getGroupByName(group.getName());

                return groupcreated;
            }
        }
        return null;
    }

    public GroupDto getGroupByName(String name){
        Optional<Groups> group = groupsRepository.findByName(name);
        return group.map(GroupDto::new).orElse(null);
    }

    public GroupDto getGroupById(int id) {
        Optional<Groups> group = groupsRepository.findById((Integer) id);
        return group.map(GroupDto::new).orElse(null);
    }

    public boolean deleteGroup(int id) {
        Optional <Groups> group = groupsRepository.findById((Integer) id);

        if(group.isPresent()) {
            Groups groupToDelete = group.get();

            groupsRepository.deleteById((Integer) groupToDelete.getId_groups());
            return true;
        }

        return false;

    }
}
