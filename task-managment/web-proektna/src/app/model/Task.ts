export class Task {
  id: string;
  taskName: string;
  taskDescription: string;
  channelId: string;
  progressBar: string;
  dueDate: string;

  constructor(id: string, taskName: string, taskDescription: string, channelId: string, progressBar: string, dueDate: string) {
    this.id = id;
    this.taskName = taskName;
    this.taskDescription = taskDescription;
    this.channelId = channelId;
    this.progressBar = progressBar;
    this.dueDate = dueDate;
  }
}
