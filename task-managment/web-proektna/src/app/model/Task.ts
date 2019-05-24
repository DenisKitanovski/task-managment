export class Task {
  id: string;
  taskName: string;
  taskDescription: string;
  channelId: string;
  progressBar: string;
  dueDate: string;
  column: string;
  position: number;

  constructor(id: string, taskName: string, taskDescription: string, channelId: string, progressBar: string, dueDate: string, column: string, position: number) {
    this.id = id;
    this.taskName = taskName;
    this.taskDescription = taskDescription;
    this.channelId = channelId;
    this.progressBar = progressBar;
    this.dueDate = dueDate;
    this.column = column;
    this.position = position;
  }
}
