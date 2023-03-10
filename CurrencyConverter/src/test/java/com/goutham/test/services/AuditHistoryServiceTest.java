package com.goutham.test.services;

import static com.goutham.test.TestConstants.TEST_QUERY_STRING;
import static com.goutham.test.TestConstants.TEST_USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.stream.IntStream;

import com.goutham.common.CurrencyConverterConstants;
import com.goutham.dao.AuditHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import com.goutham.entities.AuditEntry;
import com.goutham.entities.AuditHistory;

public class AuditHistoryServiceTest {

	private AuditHistoryService auditHistoryService;

	private AuditHistoryRepository auditHistoryRepository;

	@Before
	public void setUp() {
		auditHistoryRepository = mock(AuditHistoryRepository.class);
		auditHistoryService = new AuditHistoryServiceImpl(auditHistoryRepository);
	}

	@Test
	public void addAuditEntry_UserWithNoPreviousAuditHistory_AuditEntryListSizeIsOne() {
		// Arrange
		when(auditHistoryRepository.findOne(TEST_USER_NAME)).thenReturn(null);

		// Act
		AuditHistory result = auditHistoryService.addAuditEntry(TEST_USER_NAME, TEST_QUERY_STRING);

		// Assert
		assertThat(1).isEqualTo(result.getAuditEntries().size());
	}

	@Test
	public void addAuditEntry_UserWithPreviousAuditHistory_AuditEntryListSizeIncremented() {
		// Arrange
		AuditHistory auditHistory = createAuditHistoryDummyData();
		fillAuditEntries(auditHistory, 1);
		when(auditHistoryRepository.findOne(TEST_USER_NAME)).thenReturn(auditHistory);
		int auditEntrySize = auditHistory.getAuditEntries().size();

		// Act
		AuditHistory result = auditHistoryService.addAuditEntry(TEST_USER_NAME, TEST_QUERY_STRING);

		// Assert
		assertThat(auditEntrySize + 1).isEqualTo(result.getAuditEntries().size());
	}

	@Test
	public void addAuditEntry_UserWithPreviousAuditHistory_AuditEntryAddedToFirst() {
		// Arrange
		AuditHistory auditHistory = createAuditHistoryDummyData();
		fillAuditEntries(auditHistory, 1);
		when(auditHistoryRepository.findOne(TEST_USER_NAME)).thenReturn(auditHistory);

		// Act
		AuditHistory result = auditHistoryService.addAuditEntry(TEST_USER_NAME, TEST_QUERY_STRING);

		// Assert
		assertThat(TEST_QUERY_STRING).isEqualTo(result.getAuditEntries().getFirst().getQueryString());
	}

	@Test
	public void addAuditEntry_UserWithFullAuditHistory_DoesNotChangeAuditEntryListSize() {
		// Arrange
		AuditHistory auditHistory = createAuditHistoryDummyData();
		fillAuditEntries(auditHistory, CurrencyConverterConstants.AUDIT_ENTRIES_MAX_SIZE);
		when(auditHistoryRepository.findOne(TEST_USER_NAME)).thenReturn(auditHistory);
		int auditEntrySize = auditHistory.getAuditEntries().size();

		// Act
		AuditHistory result = auditHistoryService.addAuditEntry(TEST_USER_NAME, TEST_QUERY_STRING);

		// Assert
		assertThat(auditEntrySize).isEqualTo(result.getAuditEntries().size());
	}

	@Test
	public void addAuditEntry_UserWithFullAuditHistory_RemovesLastAuditEntry() {
		// Arrange
		AuditHistory auditHistory = createAuditHistoryDummyData();
		fillAuditEntries(auditHistory, CurrencyConverterConstants.AUDIT_ENTRIES_MAX_SIZE);
		when(auditHistoryRepository.findOne(TEST_USER_NAME)).thenReturn(auditHistory);
		AuditEntry lastAuditEntry = auditHistory.getAuditEntries().getLast();

		// Act
		AuditHistory result = auditHistoryService.addAuditEntry(TEST_USER_NAME, TEST_QUERY_STRING);

		// Assert
		assertThat(lastAuditEntry.getQueryString()).isNotEqualTo(result.getAuditEntries().getLast().getQueryString());
	}

	@Test
	public void getAuditHistoryForUser_UserWithNoPreviousAuditHistory_ReturnsEmptyAuditHistory() {
		// Arrange
		when(auditHistoryRepository.findOne(TEST_USER_NAME)).thenReturn(null);

		// Act
		AuditHistory result = auditHistoryService.getAuditHistoryForUser(TEST_USER_NAME);

		// Assert
		assertThat(0).isEqualTo(result.getAuditEntries().size());
	}

	@Test
	public void getAuditHistoryForUser_UserWithPreviousAuditHistory_ReturnsAuditHistory() {
		// Arrange
		AuditHistory auditHistory = createAuditHistoryDummyData();
		when(auditHistoryRepository.findOne(TEST_USER_NAME)).thenReturn(auditHistory);
		int auditEntrySize = auditHistory.getAuditEntries().size();

		// Act
		AuditHistory result = auditHistoryService.getAuditHistoryForUser(TEST_USER_NAME);

		// Assert
		assertThat(auditEntrySize).isEqualTo(result.getAuditEntries().size());
	}

	private AuditHistory createAuditHistoryDummyData() {
		AuditHistory auditHistory = new AuditHistory();
		LinkedList<AuditEntry> auditEntries = new LinkedList<>();
		auditHistory.setAuditEntries(auditEntries);
		return auditHistory;
	}

	private void fillAuditEntries(AuditHistory auditHistory, Integer numberOfEntries) {
		LinkedList<AuditEntry> auditEntries = auditHistory.getAuditEntries();
		IntStream.range(0, numberOfEntries).forEach((p) -> {
			auditEntries.add(new AuditEntry(Integer.toString(p), null));
		});
	}

}
