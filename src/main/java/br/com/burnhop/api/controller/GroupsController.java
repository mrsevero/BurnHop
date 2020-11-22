package br.com.burnhop.api.controller;

import br.com.burnhop.model.dto.CreatedGroupDto;
import br.com.burnhop.model.dto.GroupDto;
import br.com.burnhop.model.Groups;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.GroupsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public class GroupsController {

    GroupsRepository groupsRepository;
    UsersRepository usersRepository;

    public GroupsController(GroupsRepository groupsRepository, UsersRepository usersRepository){
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
    }

    public GroupDto createGroup(CreatedGroupDto newGroup) {
        Optional<Users> user = usersRepository.findById(newGroup.getAdmin());
        if(user.isPresent()) {
            GroupDto gpname = getGroupByName(newGroup.getName());
            if (gpname == null) {
                Groups group = new Groups(newGroup.getName(), new Timestamp(System.currentTimeMillis()));
                group.setAdmin(user.get());
                groupsRepository.save(group);

                return getGroupByName(group.getName());
            }
        }
        return null;
    }

    public ArrayList<GroupDto> getAllGroups() {
        ArrayList<GroupDto> groups = new ArrayList<>();

        for (Groups group : groupsRepository.findAll()) {
            groups.add(new GroupDto(group));
        }

        return groups;
    }

    public GroupDto getGroupByName(String name){
        Optional<Groups> group = groupsRepository.findByName(name);
        return group.map(GroupDto::new).orElse(null);
    }

    public GroupDto getGroupById(int id) {
        Optional<Groups> group = groupsRepository.findById(id);
        return group.map(GroupDto::new).orElse(null);
    }

    public boolean deleteGroup(int id) {
        Optional <Groups> group = groupsRepository.findById(id);

        if(group.isPresent()) {
            Groups groupToDelete = group.get();

            groupsRepository.deleteById(groupToDelete.getId_groups());
            return true;
        }

        return false;

    }
}
