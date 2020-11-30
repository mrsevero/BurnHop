package br.com.burnhop.api.controller;

import br.com.burnhop.model.Posts;
import br.com.burnhop.model.UsersGroups;
import br.com.burnhop.model.Groups;
import br.com.burnhop.model.Users;
import br.com.burnhop.model.dto.UserDto;
import br.com.burnhop.model.dto.GroupDto;
import br.com.burnhop.model.dto.CreatedGroupDto;
import br.com.burnhop.model.dto.UpdatedGroupDto;
import br.com.burnhop.model.dto.UsersGroupsDto;
import br.com.burnhop.model.dto.AssociatedUserGroupDto;
import br.com.burnhop.repository.GroupsRepository;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.repository.UsersGroupsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public class GroupsController {

    private final GroupsRepository groupsRepository;
    private final UsersRepository usersRepository;
    private final UsersGroupsRepository usersGroupsRepository;
    private final PostsRepository postsRepository;

    public GroupsController(GroupsRepository groupsRepository,
                            UsersRepository usersRepository,
                            UsersGroupsRepository usersGroupsRepository,
                            PostsRepository postsRepository){
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
        this.usersGroupsRepository = usersGroupsRepository;
        this.postsRepository = postsRepository;
    }

    public GroupDto createGroup(CreatedGroupDto newGroup) {
        Optional<Users> user = usersRepository.findById(newGroup.getAdmin());
        if(user.isPresent()) {
            GroupDto gpname = getGroupByName(newGroup.getName());
            if (gpname == null) {
                Groups group = new Groups(newGroup.getName(),
                        newGroup.getDescription(),
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

        Groups groupToUpdate = groupsRepository.findById(id).get();

        groupToUpdate.setName(name);
        groupToUpdate.setDescription(description);

        Groups updatedGroup = groupsRepository.save(groupToUpdate);

        return new GroupDto(updatedGroup);
    }

    public boolean deleteGroup(int id) {
        Optional <Groups> group = groupsRepository.findById(id);

        if(group.isPresent()) {
            Groups groupToDelete = group.get();

            deleteAllUsersInGroup(id);
            deleteAllPostsInGroup(id);
            groupsRepository.deleteById(groupToDelete.getId());
            return true;
        }

        return false;

    }

    public void deleteAllUsersInGroup(int groupId) {
        for (UsersGroups group : usersGroupsRepository.findAll()) {
            if(group.getGroup().getId() == groupId)
                usersGroupsRepository.deleteById(group.getIdUsersGroups());
        }
    }

    public void deleteAllPostsInGroup(int groupId) {
        for (Posts post : postsRepository.findAll()) {
            if(post.getGroup().getId() == groupId)
                postsRepository.deleteById(post.getId());
        }
    }
}
