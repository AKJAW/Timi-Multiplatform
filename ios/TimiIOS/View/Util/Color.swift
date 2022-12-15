import SwiftUI
import shared

extension TaskColor {
    func toSwiftColor() -> Color {
        return Color(red: Double(self.red), green: Double(self.green), blue: Double(self.blue))
    }
}

func accessibleFontColor(taskColor: TaskColor) -> Color {
    let red = taskColor.red
    let green = taskColor.green
    let blue = taskColor.blue
    return isLightColor(red: red, green: green, blue: blue) ? .black : .white
}

private func isLightColor(red: Float, green: Float, blue: Float) -> Bool {
    let lightRed = red > 0.65
    let lightGreen = green > 0.65
    let lightBlue = blue > 0.65

    let lightness = [lightRed, lightGreen, lightBlue].reduce(0) { $1 ? $0 + 1 : $0 }
    return lightness >= 2
}
