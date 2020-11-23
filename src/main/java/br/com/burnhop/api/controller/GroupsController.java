package br.com.burnhop.api.controller;

import br.com.burnhop.model.UsersGroups;
import br.com.burnhop.model.Groups;
import br.com.burnhop.model.Users;
import br.com.burnhop.model.dto.*;
import br.com.burnhop.repository.GroupsRepository;
import br.com.burnhop.repository.UsersGroupsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public class GroupsController {

    private final GroupsRepository groupsRepository;
    private final UsersRepository usersRepository;
    private final UsersGroupsRepository usersGroupsRepository;

    public GroupsController(GroupsRepository groupsRepository, UsersRepository usersRepository, UsersGroupsRepository usersGroupsRepository){
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
        this.usersGroupsRepository = usersGroupsRepository;
    }

    public GroupDto createGroup(CreatedGroupDto newGroup) {
        Optional<Users> user = usersRepository.findById(newGroup.getAdmin());
        if(user.isPresent()) {
            GroupDto gpname = getGroupByName(newGroup.getName());
            if (gpname == null) {
                Groups group = new Groups(newGroup.getName(),
                        newGroup.getDescription(),
                        newGroup.getGenre(),
                        new Timestamp(System.currentTimeMillis()));
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

    public ArrayList<GroupDto> getAllGroupsByUser(UserDto user) {
        ArrayList<GroupDto> groups = new ArrayList<>();

        for (UsersGroups group : usersGroupsRepository.findAll()) {
            if(group.getUser().getId() == user.getId())
                groups.add(new GroupDto(groupsRepository.findById(group.getGroup().getId()).get()));
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

    public UsersGroupsDto associateUserToGroup(AssociatedUserGroupDto userAssociated) {
        Optional<Users> user = usersRepository.findById(userAssociated.getUserId());
        Optional<Groups> group = groupsRepository.findById(userAssociated.getGroupId());

        Optional<UsersGroups> possibleUsersGroups = usersGroupsRepository.findByGroupAndUserId(
                userAssociated.getGroupId(),
                userAssociated.getUserId());

        if(possibleUsersGroups.isEmpty()) {
            UsersGroups usersGroups = new UsersGroups();
            usersGroups.setUser(user.get());
            usersGroups.setGroup(group.get());

            usersGroupsRepository.save(usersGroups);

            Optional<UsersGroups> newUsersGroups = usersGroupsRepository.findByGroupAndUserId(
                    usersGroups.getGroup().getId(), usersGroups.getUser().getId());

            if(newUsersGroups.isPresent()) {
                return new UsersGroupsDto(newUsersGroups.get());
            }
        }

        return null;
    }

    public GroupDto updateGroup(int id, GroupDto group, UpdatedGroupDto newGroup) {
        Optional<Groups> possibleGroup = groupsRepository.findByName(newGroup.getName());

        if(possibleGroup.isPresent())
            return null;

        String name = newGroup.getName().isEmpty() ?
                group.getName() :
                newGroup.getName();

        String description = newGroup.getDescription().isEmpty() ?
                group.getDescription() :
                newGroup.getDescription();

        String genre = newGroup.getGenre().isEmpty() ?
                group.getGenre() :
                newGroup.getGenre();

        Groups groupToUpdate = groupsRepository.findById(id).get();

        groupToUpdate.setName(name);
        groupToUpdate.setDescription(description);
        groupToUpdate.setGenre(genre);

        Groups updatedGroup = groupsRepository.save(groupToUpdate);

        return new GroupDto(updatedGroup);
    }

    public boolean deleteGroup(int id) {
        Optional <Groups> group = groupsRepository.findById(id);

        if(group.isPresent()) {
            Groups groupToDelete = group.get();

            groupsRepository.deleteById(groupToDelete.getId());
            return true;
        }

        return false;

    }
}
