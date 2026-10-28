package com.gopi.job.service;

import com.gopi.job.dto.ApplicationNoteResponse;
import com.gopi.job.payload.AddApplicationNoteRequest;

import java.util.List;

public interface ApplicationNoteService {

    ApplicationNoteResponse addNote(
            Long applicationId,
            Long employerId,
            AddApplicationNoteRequest req
    ) throws Exception;

    List<ApplicationNoteResponse> getNotesByApplication(
            Long applicationId,
            Long employerId
    );

    void deleteNote(
            Long applicationId,
            Long noteId,
            Long employerId
    ) throws Exception;
}
