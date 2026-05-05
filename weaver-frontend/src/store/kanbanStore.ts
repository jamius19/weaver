import { create } from 'zustand';

export interface Issue {
  id: string;
  title: string;
  description?: string;
  stageId: string;
}

export interface Stage {
  id: string;
  title: string;
  boardId: number;
}

export interface Board {
  id: number;
  title: string;
  description: string;
}

export interface Team {
  id: number;
  name: string;
  description: string;
  website: string;
  pictureLink: string;
}

interface KanbanState {
  teams: Team[];
  boards: Board[];
  stages: Stage[];
  issues: Issue[];
  
  setTeams: (teams: Team[]) => void;
  setBoards: (boards: Board[]) => void;
  setStages: (stages: Stage[]) => void;
  setIssues: (issues: Issue[]) => void;
  
  moveIssue: (issueId: string, newStageId: string, newIndex: number) => void;
}

// Temporary mock data until backend is ready for stages and issues
const MOCK_STAGES: Stage[] = [
  { id: 's1', title: 'To Do', boardId: 1 },
  { id: 's2', title: 'In Progress', boardId: 1 },
  { id: 's3', title: 'Done', boardId: 1 },
];

const MOCK_ISSUES: Issue[] = [
  { id: 'i1', title: 'Set up frontend', stageId: 's3' },
  { id: 'i2', title: 'Implement drag and drop', stageId: 's2' },
  { id: 'i3', title: 'Create backend endpoints for stages', stageId: 's1' },
];

export const useKanbanStore = create<KanbanState>((set) => ({
  teams: [],
  boards: [],
  stages: MOCK_STAGES,
  issues: MOCK_ISSUES,
  
  setTeams: (teams) => set({ teams }),
  setBoards: (boards) => set({ boards }),
  setStages: (stages) => set({ stages }),
  setIssues: (issues) => set({ issues }),
  
  moveIssue: (issueId, newStageId, newIndex) => set((state) => {
    const issues = [...state.issues];
    const issueIndex = issues.findIndex(i => i.id === issueId);
    if (issueIndex > -1) {
      const [issue] = issues.splice(issueIndex, 1);
      issue.stageId = newStageId;
      
      // Calculate new position among issues in the new stage
      const issuesInNewStage = issues.filter(i => i.stageId === newStageId);
      if (newIndex >= issuesInNewStage.length) {
        issues.push(issue);
      } else {
        const targetIssue = issuesInNewStage[newIndex];
        const targetIndex = issues.indexOf(targetIssue);
        issues.splice(targetIndex, 0, issue);
      }
    }
    return { issues };
  }),
}));
