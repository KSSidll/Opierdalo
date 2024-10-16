package com.kssidll.opierdalo.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kssidll.opierdalo.ExpandedPreviews
import com.kssidll.opierdalo.ui.theme.OpierdaloTheme
import com.kssidll.opierdalo.ui.theme.Typography

@Composable
fun SizeAvailabilityIcon(
    size: String,
    amount: Int,
    modifier: Modifier = Modifier,
    spacing: Dp = 3.dp,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = contentColorFor(color),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = amount.toString(),
            style = Typography.bodyMedium,
        )

        Spacer(modifier = Modifier.width(spacing))

        Surface(
            shape = CircleShape,
            color = color,
            contentColor = contentColor
        ) {
            Box(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = size,
                    style = Typography.bodySmall
                )
            }
        }
    }
}

@PreviewLightDark
@ExpandedPreviews
@Composable
private fun SizeAvailabilityIconPreview() {
    OpierdaloTheme {
        Surface {
            SizeAvailabilityIcon(
                size = "XL",
                amount = 0
            )
        }
    }
}
