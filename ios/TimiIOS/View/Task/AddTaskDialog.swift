import SwiftUI
import shared

struct AddTaskDialog: ViewModifier {
    
    @Binding var isShowing: Bool
    var addTask: (String, TaskColor) -> Void
    @State private var taskName: String = ""
    
    init(isShowing: Binding<Bool>, addTask: @escaping (String, TaskColor) -> Void) {
        _isShowing = isShowing
        self.addTask = addTask
    }
    
    func body(content: Content) -> some View {
        ZStack {
            content
            if isShowing {
                // the semi-transparent overlay
                Rectangle().foregroundColor(Color.black.opacity(0.6))
                    .onTapGesture {
                        isShowing = false
                    }
                VStack {
                    TextField(
                        "Task name",
                        text: $taskName
                    )
                        .onSubmit { onSubmit() }
                    HStack {
                        Button("Add", action: { onSubmit() })
                    }
                }
                .padding()
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .foregroundColor(.white))
                .onTapGesture {}
                .padding(40)
            }
        }
    }
    
    func onSubmit() {
        print(taskName)
        isShowing = false
        addTask(
            taskName,
            TaskColor(
                red: Float.random(in: 0..<1),
                green: Float.random(in: 0..<1),
                blue: Float.random(in: 0..<1)
            )
        )
    }
}

extension View {
    func addTaskDialog(
        isShowing: Binding<Bool>,
        addTask: @escaping (String, TaskColor) -> Void
    ) -> some View {
        self.modifier(AddTaskDialog(isShowing: isShowing, addTask: addTask))
    }
}

//struct AddTaskDialog_Previews: PreviewProvider {
//    static var previews: some View {
//        AddTaskDialog(isShowing: .constant(true), presenting: self)
//    }
//}
